def __init__(self, * args, ** kwargs) :
	wx.Frame.__init__(self, * args, ** kwargs)
	self.panel = wx.Panel(self)
	self.button = wx.Button(self.panel, label = "Test")
	self.sizer = wx.BoxSizer()
	self.sizer.Add(self.button)
	self.panel.SetSizerAndFit(self.sizer)
	self.Show()


def __init__(self, parent, * args, ** kwargs) :
	super(MenuBar, self).__init__(* args, ** kwargs)
	file_menu = wx.Menu()
	self.Append(file_menu, '&File')
	quit_menu_item = wx.MenuItem(file_menu, wx.ID_EXIT)
	parent.Bind(wx.EVT_MENU, parent.on_quit_click, id = wx.ID_EXIT)
	file_menu.Append(quit_menu_item)

