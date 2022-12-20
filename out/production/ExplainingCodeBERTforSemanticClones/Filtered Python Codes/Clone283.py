def meta_redirect(content) :
	root = soupparser.fromstring(content)
	result_url = root.xpath('//meta[@http-equiv="refresh"]/@content')
	if result_url :
		result_url = str(result_url [0])
		urls = result_url.split('URL=') if len(result_url.split('url=')) < 2 else result_url.split('url=')
		url = urls [1] if len(urls) > = 2 else None
	else :
		return None
	return url


def meta_redirect(content) :
	soup = BeautifulSoup.BeautifulSoup(content)
	result = soup.find("meta", attrs = {"http-equiv" : "Refresh"})
	if result :
		wait, text = result ["content"].split(";")
		if text.strip().lower().startswith("url=") :
			url = text [4 :]
			return url
	return None

