def access(obj, indexes) :
	try :
		return reduce(list.__getitem__, indexes, obj)
	except Exception :
		return None


def access(obj, indexes) :
	def _get_item(subobj, index) :
		if isinstance(subobj, list) and index < len(subobj) :
			return subobj [index]
		return None
	return reduce(_get_item, indexes, obj)

