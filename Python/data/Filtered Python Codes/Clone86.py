def function(argument) :
	max_arg = max(argument)
	argument.remove(max_arg)
	for length in range(1, len(argument) + 1) :
		for combo in combinations(argument, length) :
			if sum(combo) == max_arg :
				return "true"
	return "false"


def function(argument) :
	max_arg = max(argument)
	argument.remove(max_arg)
	s = 0
	for i in argument :
		s += i
		if s == max_arg :
			return "true"
	return "false"

