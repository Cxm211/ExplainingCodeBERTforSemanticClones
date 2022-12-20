def reverseParentheses(s) :
	assert '(' in s and ')' in s
	while '(' in s :
		reverseParentheses(s)
	return s


def reverseParentheses(s) :
	def reverse_interior(m) :
		s = m.group()
		return s [- 2 : 0 : - 1]
	old = ""
	while old ! = s :
		old = s
		s = re.sub(r'(\([^\(\)]*\))', reverse_interior, s)
	return s

