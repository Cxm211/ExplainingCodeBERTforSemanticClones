def do_loop(self) :
	for line in self.connections [0].iter_lines() :
		if self.new_conn.is_set() :
			break
		print line


def do_loop(self) :
	while true :
		conn = copy(connections [0])
		for line in conn.iter_lines() :
			print line
			if conn ! = connections [0] :
				break

