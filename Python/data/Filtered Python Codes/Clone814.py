def get_icon_path(extension, size = 32) :
	type_, encoding = mimetypes.guess_type('x.' + extension)
	if type_ :
		icon = gio.content_type_get_icon(type_)
		theme = gtk.icon_theme_get_default()
		info = theme.choose_icon(icon.get_names(), size, 0)
		if info :
			return info.get_filename()


def get_icon_path(mimetype, size = 32) :
	icon = Gio.content_type_get_icon(mimetype)
	theme = Gtk.IconTheme.get_default()
	info = theme.choose_icon(icon.get_names(), size, 0)
	if info :
		print (info.get_filename())

