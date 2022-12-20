def default(self, v) :
	types = {
	'ObjectId' : lambda v : str(v),
	'datetime' : lambda v : v.isoformat()}
	vtype = type(v).__name__
	if vtype in types :
		return types [type(v).__name__](v)
	else :
		return json.JSONEncoder.default(self, v)


def default(self, obj) :
	if isinstance(obj, TYPES.values()) :
		key = '__%s__' % obj.__class__.__name__
		return {key : obj.__dict__}
	return json.JSONEncoder.default(self, obj)

