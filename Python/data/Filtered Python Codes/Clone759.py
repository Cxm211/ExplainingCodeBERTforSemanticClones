def queryset(self, request, queryset) :
	if self.value() :
		return set(comment for comment in queryset if comment.posted_by_guest())
	elif not self.value() :
		return set(comment for comment in queryset if not comment.posted_by_guest())


def queryset(self, request, queryset) :
	expected_value = self.value()
	excludes = []
	for comment in queryset :
		if comment.posted_by_guest() ! = expected_value :
			excludes.append(comment.id)
	return queryset.exclude(pk__in = excludes)

