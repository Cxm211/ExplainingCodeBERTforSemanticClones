def __init__(cls, * a, ** k) :
	mkl = cls.__class__
	class spec(mkl) : pass
	for n, v in vars(cls).items() :
		if isinstance(v, const) :
			setattr(spec, n, v)
	spec.__name__ = mkl.__name__
	cls.__class__ = spec


def __init__(cls, name, bases, dct) :
	type.__init__(cls, name, bases, dct)
	old_setattr = cls.__setattr__
	def __setattr__(self, key, value) :
		cls.assert_attribute_mutable(key)
		old_setattr(self, key, value)
	cls.__setattr__ = __setattr__

