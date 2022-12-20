def productFib(prod) :
	power = math.log(prod * 5) / log_phi
	i = int(round(power - 1, 7) / 2) + 1
	low = f(i)
	high = f(i + 1)
	answer = [low, high, low * high == prod]
	return answer


def productFib(prod) :
	a, b = 0, 1
	while prod > a * b :
		a, b = b, a + b
	return [a, b, prod == a * b]

