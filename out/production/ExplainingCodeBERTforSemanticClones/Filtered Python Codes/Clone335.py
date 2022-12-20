def show_progress(block_num, block_size, total_size) :
	global pbar
	if pbar is None :
		pbar = progressbar.ProgressBar(maxval = total_size)
	downloaded = block_num * block_size
	if downloaded < total_size :
		pbar.update(downloaded)
	else :
		pbar.finish()
		pbar = None


def show_progress(count, block_size, total_size) :
	if pbar is None :
		pbar = ProgressBar(maxval = total_size)
	downloaded += block_size
	pbar.update(block_size)
	if downloaded == total_size :
		pbar.finish()
		pbar = None
		downloaded = 0

