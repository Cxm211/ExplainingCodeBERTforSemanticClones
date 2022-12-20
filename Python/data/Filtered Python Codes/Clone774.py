def __init__(self) :
	wx.Frame.__init__(self, None, - 1, "Test", size = (500, 270))
	panel = wx.Panel(self, - 1)
	self.buttonStart = wx.Button(panel, - 1, label = "Start thread", pos = (0, 0))
	self.buttonChange = wx.Button(panel, - 1, label = "Change var", pos = (0, 30))
	panel.Bind(wx.EVT_BUTTON, self.startThread, id = self.buttonStart.GetId())
	panel.Bind(wx.EVT_BUTTON, self.changeVar, id = self.buttonChange.GetId())


def __init__(self) :
	wx.Frame.__init__(self, None, - 1, "Test", size = (500, 270))
	panel = wx.Panel(self, - 1)
	self.buttonStart = wx.Button(panel, - 1, label = "Start timer", pos = (0, 0))
	self.buttonChange = wx.Button(panel, - 1, label = "Change var", pos = (0, 30))
	panel.Bind(wx.EVT_BUTTON, self.startTimer, id = self.buttonStart.GetId())
	panel.Bind(wx.EVT_BUTTON, self.changeVar, id = self.buttonChange.GetId())
	self.value = 1
	self.timer = wx.Timer(self)
	self.Bind(wx.EVT_TIMER, self.printer, self.timer)

