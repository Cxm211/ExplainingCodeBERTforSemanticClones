def get_info(session, title, url) :
	r = session.get(url)
	soup = BeautifulSoup(r.text, "lxml")
	for items in soup.select("ul.list-unstyled") :
		if len(items.select("a[href^='tel:']")) :
			phone = items.select("a[href^='tel:']") [0].text
			break
		else :
			phone = "N/A"
	print (title, phone)


def get_info(session, title, url) :
	response = requests.get(url)
	soup = BeautifulSoup(response.content, "lxml")
	script = next((i for i in map(str, soup.find_all("script", type = "text/javascript"))
	if 'mapOptions' in i), None)
	if script :
		js_dict = script.split('__mapOptions = ') [1].split(';\n') [0]
		d = yaml.load(js_dict)
		print (title, d ['mapStore'] ['phone'])

