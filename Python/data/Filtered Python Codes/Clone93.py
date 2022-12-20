def checksum(msg) :
	s = 0
	for i in range(0, len(msg), 2) :
		w = ord(msg [i]) + (ord(msg [i + 1]) < < 8)
		s = carry_around_add(s, w)
	return ~ s & 0xffff


def checksum(pkt) :
	if len(pkt) % 2 == 1 :
		pkt += "\0"
	s = sum(array.array("H", pkt))
	s = (s >> 16) + (s & 0xffff)
	s += s >> 16
	s = ~ s
	return s & 0xffff

