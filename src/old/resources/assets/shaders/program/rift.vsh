#version 120

attribute vec4 Position;

varying vec4 worldPoint;
varying vec2 unprojectedPoint;

void main() {
    unprojectedPoint = Position.xy;
    worldPoint = gl_ModelViewMatrix * Position;
    gl_Position = gl_ProjectionMatrix * worldPoint;
}
