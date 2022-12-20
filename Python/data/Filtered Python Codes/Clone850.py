def __init__(self) :
	self.w, self.h = 800, 600
	PygameHelper.__init__(self, size = (self.w, self.h), fill = ((255, 255, 255)))
	self.img = pygame.image.load("colors.png")
	self.screen.blit(self.img, (0, 0))
	self.drawcolor = (0, 0, 0)
	self.x = 0


def __init__(self) :
	os.environ ['SDL_VIDEO_CENTERED'] = '1'
	pg.init()
	self.w, self.h = 800, 600
	self.screen = pg.display.set_mode((self.w, self.h))
	self.screen.fill(pg.Color('white'))
	self.clock = pg.time.Clock()
	self.drawcolor = (0, 0, 0)

