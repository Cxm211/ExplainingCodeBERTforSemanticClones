def readParag(fileObj) :
	while True :
		nextList = [ln.rstrip() for ln in takewhile(lambda line : line ! = "\n", fileObj)]
		if not nextList :
			break
		yield nextList


def readParag(filename) :
	with open(filename) as f :
		while True :
			paras = itertools.takewhile(lambda l : l.strip(), f)
			test, paras = itertools.tee(paras)
			test.next()
			yield (l.strip() for l in paras)

