def merge_sort(sequence) :
	if len(sequence) < 2 :
		return sequence
	mid = len(sequence) / / 2
	left_sequence = merge_sort(sequence [: mid])
	right_sequence = merge_sort(sequence [mid :])
	return merge(left_sequence, right_sequence)


def merge_sort(arr, p, r) :
	if p < r :
		q = (p + r) / / 2
		merge_sort(arr, p, q)
		merge_sort(arr, q + 1, r)
		merge(arr, p, q, r)

