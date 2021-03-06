#version 330
layout(location = 0) in vec3 position;

out vec2 TexCoord;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;

void main() {
	 gl_Position = viewMatrix* modelMatrix*vec4(position, 1.0f);
	 TexCoord = vec2(position.x*2+0.5, position.y*2+0.5);
 }
