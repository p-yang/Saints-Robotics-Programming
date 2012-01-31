//#include <stdint.h>

#include "accelFilter.h"
int32_t max_v = 18;
int32_t max_a = 5;

int32_t sd(int32_t v, int32_t a)
{
	if (v < 0) {
		v = -v;
		return -(v + v % a) * (v - v % a + a) / (2 * a);
	} else {
		return (v + v % a) * (v - v % a + a) / (2 * a);
	}
}

int32_t get_sign(int32_t i)
{
	if (i > 0) {
		return 1;
	} else if (i < 0) {
		return -1;
	} else {
		return 0;
	}
}

int32_t get_acceleration (int32_t distance_to_target, int32_t v, int32_t goal_v)
{
	int32_t sign = get_sign(distance_to_target);
	int32_t gooda;
        // Compute our maximum acceleration
	if (sign * v + max_a > max_v) {
		gooda = sign * max_v - v ;
	} else {
		gooda = sign * max_a;
	}
        // Loop while accelerating that way would throw us too far
	while (sign * (v + sd(v + gooda, max_a) - sd(goal_v, max_a) - distance_to_target) > 0) {
		gooda = gooda - sign;
	} 
	return gooda;
}
// To use this, do
// v = v + get_acceleration(goal - pos, v, goal_v)
// pos = pos + v
