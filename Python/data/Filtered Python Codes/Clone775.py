def test_func_happy_path(self, MockFTP, m_open) :
	MockFTP.return_value = Mock()
	mock_ftp_obj = MockFTP()
	m_open.return_value = Mock()
	func('localhost', 'fred', 's3Kr3t')
	assert mock_ftp_obj.retrbinary.called
	assert m_open.called
	m_open.assert_called_once_with('README', 'wb')


def test_func_happy_path(MockFTP) :
	mock_ftp = MockFTP.return_value
	with patch('__main__.open', mock_open(), create = True) as m :
		func('localhost', 'fred', 's3Kr3t')
	assert mock_ftp.retrbinary.called
	m.assert_called_once_with('README', 'wb')

