def __init__(self, parent, * args, ** kwargs) :
	super(MainPanel, self).__init__(parent, * args, ** kwargs)
	"""Create and populate main sizer."""
	sizer = wx.BoxSizer(wx.VERTICAL)
	cmd_quit = wx.Button(self, id = wx.ID_EXIT)
	cmd_quit.Bind(wx.EVT_BUTTON, parent.on_quit_click)
	sizer.Add(cmd_quit)
	self.SetSizer(sizer)


def __init__(self, parent, * args, ** kwargs) :
	super(MenuBar, self).__init__(* args, ** kwargs)
	file_menu = wx.Menu()
	self.Append(file_menu, '&File')
	quit_menu_item = wx.MenuItem(file_menu, wx.ID_EXIT)
	parent.Bind(wx.EVT_MENU, parent.on_quit_click, id = wx.ID_EXIT)
	file_menu.Append(quit_menu_item)

