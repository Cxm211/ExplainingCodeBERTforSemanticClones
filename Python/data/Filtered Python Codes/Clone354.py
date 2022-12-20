def __init__(self, master, ** kw) :
	super().__init__(master, ** kw)
	self.entry = tkinter.Entry(self)
	self.text = tkinter.Text(self, height = 15, width = 50,
	wrap = WORD, state = DISABLED)
	self.entry.grid(row = 0, column = 0, sticky = NSEW)
	self.text.grid(row = 1, column = 0, sticky = NSEW)
	self.grid_rowconfigure(1, weight = 1)
	self.grid_columnconfigure(0, weight = 1)
	on_validate(self.entry, Mode.key, self.validator)


def __init__(self, parent) :
	tk.Frame.__init__(self, parent)
	vcmd = (self.register(self.onValidate),
	'%d', '%i', '%P', '%s', '%S', '%v', '%V', '%W')
	self.entry = tk.Entry(self, validate = "key", validatecommand = vcmd)
	self.text = tk.Text(self, height = 10, width = 40)
	self.entry.pack(side = "top", fill = "x")
	self.text.pack(side = "bottom", fill = "both", expand = True)

