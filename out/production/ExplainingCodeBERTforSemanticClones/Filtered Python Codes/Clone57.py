def createfile() :
	var = """\
	#!/bin/sh
	echo ${test}
	"""
	var = textwrap.dedent(var)
	funcToCreateScript(filename, var)


def createfile() :
	var = """#!/bin/sh
	echo ${test}
	"""
	script_txt = ""
	for line in var :
		script_txt += line.lstrip(" ")
	return script_txt

