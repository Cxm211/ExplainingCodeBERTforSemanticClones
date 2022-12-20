def DnaCheck() :
	if any(c in squence_str for c in ['A', 'C', 'T', 'G']) :
		return "yes"
	else :
		return "no"


def DnaCheck() :
	squence_str = set(squence_str.upper())
	for char in ['A', 'C', 'T', 'G'] :
		if char not in squence_str :
			return False
	return True

