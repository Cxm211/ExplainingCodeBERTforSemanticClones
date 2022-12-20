def pdf_view(request) :
	try :
		return FileResponse(open('foobar.pdf', 'rb'), content_type = 'application/pdf')
	except FileNotFoundError :
		raise Http404()


def pdf_view(request) :
	with open('/path / to /name.pdf', 'rb') as pdf :
		response = HttpResponse(pdf.read(), content_type = 'application/pdf')
		response ['Content-Disposition'] = 'filename=some_file.pdf'
		return response

