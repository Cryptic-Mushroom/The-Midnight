#version 120

const int GL_EXP = 2048;
const int GL_EXP2 = 2049;
const int GL_LINEAR = 9729;

const float GLOW_RADIUS = 0.3;
const float GLOW_RADIUS_SQ = GLOW_RADIUS * GLOW_RADIUS;

const float TEXEL_SIZE = 0.0625;

const float NOISE_FREQUENCY = 1.0;
const float NOISE_SPEED = 0.01;

const float PULSE_SPEED = 0.05;
const float PULSE_MAX = 1.15;

const float GLOW_BRIGHTNESS = 0.4;

const float RED_FACTOR = 1.0 * GLOW_BRIGHTNESS;
const float GREEN_FACTOR = 0.3 * GLOW_BRIGHTNESS;
const float BLUE_FACTOR = 0.35 * GLOW_BRIGHTNESS;

const vec2 ORIGIN = vec2(0.0, 0.0);

uniform vec2 vertices[12];
uniform float Time;

uniform sampler2D NoiseSampler;

uniform float FogMode;

varying vec4 worldPoint;
varying vec2 unprojectedPoint;

vec2 pixelate(vec2 point) {
    return vec2(floor(point.s / TEXEL_SIZE) * TEXEL_SIZE, floor(point.y / TEXEL_SIZE) * TEXEL_SIZE);
}

float distanceSq(vec2 a, vec2 b) {
    vec2 displacement = b - a;
    return dot(displacement, displacement);
}

float distanceLineSq(vec2 point, vec2 a, vec2 b) {
    float lengthSq = distanceSq(a, b);
    float t = dot(point - a, b - a) / lengthSq;

    if (t < 0.0) {
        return distanceSq(point, a);
    } else if (t > 1.0) {
        return distanceSq(point, b);
    }

    vec2 projected = a + t * (b - a);
    return distanceSq(point, projected);
}

float computeEdgeDistanceSq(vec2 point) {
    float edgeDistanceSq = 1e5;
    for (int i = 0; i < 12; i++) {
        vec2 a = vertices[i];
        vec2 b = i < 12 - 1 ? vertices[i + 1] : vertices[0];

        float distanceSq = distanceLineSq(point, a, b);
        if (distanceSq < edgeDistanceSq) {
            edgeDistanceSq = distanceSq;
        }
    }

    return edgeDistanceSq;
}

float computeGlowFactor(float edgeDistanceSq) {
    float normalizedDistance = sqrt(edgeDistanceSq) / GLOW_RADIUS;
    return 1.0 - clamp(normalizedDistance, 0.0, 1.0);
}

float sign(vec2 p1, vec2 p2, vec2 p3) {
    return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
}

bool segmentContains(vec2 point, vec2 a, vec2 b) {
    float d1 = sign(point, ORIGIN, a);
    float d2 = sign(point, a, b);
    float d3 = sign(point, b, ORIGIN);

    bool has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
    bool has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

    return !(has_neg && has_pos);
}

bool contains(vec2 point) {
    for (int i = 0; i < 12; i++) {
        vec2 a = vertices[i];
        vec2 b = i < 12 - 1 ? vertices[i + 1] : vertices[0];
        if (segmentContains(point, a, b)) {
            return true;
        }
    }
    return false;
}

float noise(vec2 point) {
    return texture2D(NoiseSampler, point * NOISE_FREQUENCY).x;
}

float computeNoiseFactor(vec2 point) {
    float noiseTime = Time * NOISE_SPEED;

    float noiseFactor = 0.0;
    noiseFactor += noise(point + vec2(noiseTime * -0.5, noiseTime * 0.7));
    noiseFactor += noise(point + vec2(noiseTime * 0.3, noiseTime * -0.4));
    noiseFactor += noise(point + vec2(noiseTime * -0.6, noiseTime * -0.2));

    return noiseFactor / 3.0;
}

float computeNoiseAddition(vec2 point) {
    float noiseTime = Time * NOISE_SPEED * 0.25;
    point = point * 0.1;

    float noiseAddition = 0.0;
    noiseAddition += noise(point + vec2(noiseTime * -0.5, noiseTime * 0.7));
    noiseAddition += noise(point + vec2(noiseTime * 0.3, noiseTime * -0.4));
    noiseAddition += noise(point + vec2(noiseTime * -0.6, noiseTime * -0.2));

    return noiseAddition / 3.0;
}

float computeFogIntensity(vec3 point) {
    highp int fogMode = int(FogMode);
    if (fogMode == GL_LINEAR) {
        return clamp((length(point) - gl_Fog.start) * gl_Fog.scale, 0.0, 1.0);
    } else if (fogMode == GL_EXP) {
        return 1.0 - clamp(exp(-gl_Fog.density * length(point)), 0.0, 1.0);
    } else if (fogMode == GL_EXP2) {
        return 1.0 - clamp(exp(-pow(gl_Fog.density * length(point), 2.0)), 0.0, 1.0);
    }
    return 0.0;
}

vec4 applyFog(vec4 color, vec4 point) {
    float fogIntensity = computeFogIntensity(point.xyz);
    return mix(color, gl_Fog.color, fogIntensity);
}

void main() {
    vec2 pixelatedPoint = pixelate(unprojectedPoint);

    float edgeDistanceSq = computeEdgeDistanceSq(pixelatedPoint);

    float alpha = 1.0;

    float glowFactor = computeGlowFactor(edgeDistanceSq);
    if (!contains(pixelatedPoint)) {
        alpha = pow(glowFactor, 2.0);
    }

    if (alpha <= 0.01) {
        discard;
    }

    float noiseFactor = computeNoiseFactor(pixelatedPoint);
    float noiseAddition = pow(computeNoiseAddition(pixelatedPoint), 5.0);
    float glowPulse = (sin(Time * PULSE_SPEED) + 1.0) / 2.0 * PULSE_MAX;

    float resultGlow = glowFactor * noiseFactor * (glowPulse + 1.0) + noiseAddition;

    float red = resultGlow * RED_FACTOR;
    float green = resultGlow * GREEN_FACTOR;
    float blue = resultGlow * BLUE_FACTOR;

    gl_FragColor = applyFog(vec4(red, green, blue, alpha), worldPoint);
}
