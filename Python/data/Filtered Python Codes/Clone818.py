def setup(self) :
	import os.path as op
	self.fixture_dir = op.join(op.abspath(op.dirname(__file__)), "fixtures")
	if not os.access(self.fixture_dir, os.F_OK) :
		raise AssertionError("Oops! "
		"the fixture dir should be here " + self.fixture_dir)
	csvfile = op.join(self.fixture_dir, "profiles-source1.csv")
	assert os.access(csvfile, os.F_OK)


def setup(self) :
	from os.path import abspath, dirname
	from os import access, F_OK
	fixture_dir = abspath(dirname(__file__)) + "/fixtures/"
	self.fixture_dir = fixture_dir
	exists = access(fixture_dir, F_OK)
	assert exists, "Oops! the fixture dir should be here " + fixture_dir
	assert access(fixture_dir + "profiles-source1.csv", F_OK)

