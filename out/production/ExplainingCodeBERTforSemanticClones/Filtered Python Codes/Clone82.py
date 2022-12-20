def get(self, request, * args, ** kwargs) :
	context = self.get_context_data()
	response = HttpResponse(content_type = 'application/pdf')
	response ['Content-Disposition'] = 'inline; filename="worksheet_pdf.pdf"'
	return response


def get(self, request, * args, ** kwargs) :
	objkey = self.kwargs.get('pk', None)
	pdf = get_object_or_404(Pdf, pk = objkey)
	fname = pdf.filename()
	path = os.path.join(settings.MEDIA_ROOT, 'docs\\' + fname)
	response = FileResponse(open(path, 'rb'), content_type = "application/pdf")
	response ["Content-Disposition"] = "filename={}".format(fname)
	return response

