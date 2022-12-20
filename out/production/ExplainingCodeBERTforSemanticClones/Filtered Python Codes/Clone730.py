def hit(bx, by, r, px, py, h) :
	if bx > = px - r and py < = by < = py + h :
		True
	else :
		False


def hit(bx, by, r, px, py, h) :
	if by > = py and by < = py + h :
		print "Y satisfied."
		if bx < = px + r :
			print "HIT"
			return True
		print "X not satisfied."
	print "not hit."
	return False

