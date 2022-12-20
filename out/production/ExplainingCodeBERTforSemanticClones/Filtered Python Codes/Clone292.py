def eval_expr(cls, expr, subs) :
	'''IMPORTANT: this is class method, overload it with @classmethod!
	Evaluate an expression given in the expr string.
	:param expr: str. String expression.
	:param subs: dict. Dictionary with values to substitute.
	:returns: Evaluated expression result.
	'''


def eval_expr(cls, expr, subs = None) :
	if subs is None :
		frame = sys._getframe()
		subs = {}
		subs.update(frame.f_globals)
		if frame.f_back :
			subs.update(frame.f_back.f_globals)
	expr_tree = ast.parse(expr, mode = 'eval').body
	return cls.eval(expr_tree, subs)

