def DnaCheck() :
	if any(c in squence_str for c in ['A', 'C', 'T', 'G']) :
		return "yes"
	else :
		return "no"


def DnaCheck() :
	for character in ['A', 'C', 'T', 'G'] :
		if character in (squence_str.upper()) :
			print "yes"
			break
	else :
		print "no"

