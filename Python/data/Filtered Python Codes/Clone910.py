def __init__(self, parent, move_widget) :
	super(Grip, self).__init__(parent)
	self.move_widget = move_widget
	self.setText("+")
	self.min_height = 50
	self.mouse_start = None
	self.height_start = self.move_widget.height()
	self.resizing = False
	self.setMouseTracking(True)
	self.setCursor(QtCore.Q.SizeVerCursor)


def __init__(self) :
	super(Dialog, self).__init__()
	layout = QtGui.QVBoxLayout()
	self.setLayout(layout)
	list_widget = QtGui.QListWidget()
	layout.addWidget(list_widget)
	gripper = Grip(self, list_widget)
	layout.addWidget(QtGui.QLabel("Test"))
	self.setGeometry(200, 500, 200, 500)

