def get_file_list(directory = '.') :
	files = []
	for i in os.listdir(directory) :
		if os.path.isdir(i) :
			files.extend(get_file_list(i))
		else :
			files.append(i)
	return files


def get_file_list(directory = os.getcwd()) :
	def file_list(directory, files) :
		for i in os.listdir(directory) :
			if os.path.isdir(i) :
				file_list(i, files)
				continue
			files.append(i)
		return files
	return file_list(directory, [])

