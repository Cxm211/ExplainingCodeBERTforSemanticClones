def save(self, * args, ** kwargs) :
	imageTemproary = Image.open(self.uploadedImage)
	outputIoStream = BytesIO()
	imageTemproaryResized = imageTemproary.resize((1020, 573))
	imageTemproaryResized.save(outputIoStream, format = 'JPEG', quality = 85)
	outputIoStream.seek(0)
	self.uploadedImage = InMemoryUploadedFile(outputIoStream, 'ImageField', "%s.jpg" % self.uploadedImage.name.split('.') [0], 'image/jpeg', sys.getsizeof(outputIoStream), None)
	super(ImageUpload, self).save(* args, ** kwargs)


def save(self, * args, ** kwargs) :
	if self.photo :
		image = Img.open(StringIO.StringIO(self.photo.read()))
		image.thumbnail((200, 200), Img.ANTIALIAS)
		output = StringIO.StringIO()
		image.save(output, format = 'JPEG', quality = 75)
		output.seek(0)
		self.photo = InMemoryUploadedFile(output, 'ImageField', "%s.jpg" % self.photo.name, 'image/jpeg', output.len, None)
	super(Mymodel, self).save(* args, ** kwargs)

