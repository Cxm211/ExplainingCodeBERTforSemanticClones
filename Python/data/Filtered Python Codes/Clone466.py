def insert_sequence(dna1, dna2, number) :
	result = '';
	for ind, x in enumerate(dna1) :
		if ind == number :
			result = result + dna2 + x
		else :
			result = result + x
	print (result)


def insert_sequence(dna1, dna2, number) :
	'''(str, str, int) -> str
	Return the DNA sequence obtained by inserting the second DNA sequence
	at the given index. (You can assume that the index is valid.)
	>>> insert_sequence('CCGG', 'AT', 2)
	'CCATGG'
	>>> insert_sequence('TTGC', 'GG', 2)
	'TTGGGC'
	'''
	return dna1 [: number] + dna2 + dna1 [number :]

