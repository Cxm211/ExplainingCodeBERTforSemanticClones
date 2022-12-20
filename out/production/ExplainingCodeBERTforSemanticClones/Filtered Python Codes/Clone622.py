def merge_sort(data) :
	if len(data) == 1 :
		return data
	middle = len(data) / / 2
	left_data = merge_sort(data [: middle])
	right_data = merge_sort(data [middle :])
	return merge(left_data, right_data)


def merge_sort(arr, p, r) :
	if p < r :
		q = (p + r) / / 2
		merge_sort(arr, p, q)
		merge_sort(arr, q + 1, r)
		merge(arr, p, q, r)

