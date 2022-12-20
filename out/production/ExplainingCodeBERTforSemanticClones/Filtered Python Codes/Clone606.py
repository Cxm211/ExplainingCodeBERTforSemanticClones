def sigmoid(x) :
	"Numerically-stable sigmoid function."
	if x > = 0 :
		z = exp(- x)
		return 1 / (1 + z)
	else :
		z = exp(x)
		return z / (1 + z)


def sigmoid(x) :
	try :
		res = 1 / (1 + math.exp(- x))
	except OverflowError :
		res = 0.0
	return res

