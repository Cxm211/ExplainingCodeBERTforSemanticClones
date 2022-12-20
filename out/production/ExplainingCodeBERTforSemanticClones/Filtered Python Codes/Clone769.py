def search(request) :
	if request.method == "GET" :
		search_terms = request.GET ['title']
		search_terms = search_terms.split(',')
		search_terms = set(search_terms)
		queryargs = [Q(title__contains = i) for i in search_terms]
		jobs = Job.objects.filter(* queryargs)


def search(request) :
	if request.method == "GET" :
		search_terms = request.GET ['title']
		search_filter = Q()
		for term in search_terms.split(',') :
			search_filter |= Q(title__contains = term)
		jobs = Job.objects.filter(search_filter)

