#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture; // The texture being sampled
uniform float u_time;        // Time variable for animation

void main() {
    // Calculate distortion based on the bubble effect
    vec2 center = vec2(0.5, 0.5); // Center of the bubble
    vec2 coord = v_texCoords - center; // Shift coordinates to bubble center
    float distance = length(coord); // Distance from the center

    // Create a bubble distortion effect
    float radius = 0.1; // Radius of the bubble
    float distortion = 0.1 * sin(distance * 10.0 - u_time * 3.0); // Animated distortion

    // Apply distortion only within the bubble's radius
    if (distance < radius) {
        coord += coord * distortion;
    }

    coord += center; // Shift coordinates back to texture space

    // Handle texture wrapping (prevent sampling outside texture bounds)
    coord = clamp(coord, 0.0, 1.0);

    // Sample the texture with distorted coordinates
    vec4 color = texture2D(u_texture, coord);

    // Add a subtle highlight to simulate light reflection on the bubble
    float highlight = smoothstep(0.3, 0.0, abs(distance - radius + 0.05));
    vec3 lightColor = vec3(1.0, 1.0, 1.0); // White light
    color.rgb += lightColor * highlight * 0.6;

    // Final color output
    gl_FragColor = color * v_color;
}
