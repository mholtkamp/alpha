precision mediump float;

varying vec2 v_pos;
varying vec4 v_color;
varying vec2 v_texCoord0;

uniform float offset;

const vec2 c_origin = vec2(-1.2,-1.2);
const vec2 c_sp = vec2(1.0,0.0);

uniform sampler2D u_sampler2D;

void main() {

    vec2 dv = normalize(v_pos - c_origin);
    float ang = acos(dot(dv,c_sp)/(length(dv)*length(c_sp)));
    ang += offset;
    if(	(ang < 0.393)&&(ang > 0.293)||
    	(ang < 0.785)&&(ang > 0.685)||
    	(ang < 1.178)&&(ang > 1.078)||
    	(ang < 1.571)&&(ang > 1.471))
    {
   	 if((texture2D(u_sampler2D, v_texCoord0) * v_color).a > 0.6)
   	 gl_FragColor = (texture2D(u_sampler2D, v_texCoord0) * v_color + vec4(0.7,0.0,0.0,0.2));
   	    // gl_FragColor = vec4(0.7,0.0,0.0,1.0);
   	    
   	 }
   	
   	else
   	 gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * v_color;
   	

}