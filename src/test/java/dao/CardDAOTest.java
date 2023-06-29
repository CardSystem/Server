package dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dao.CardDao;
import dto.CardHistoryDto;

public class CardDaoTest {

    private CardDao CardDao;
    private CardHistoryDto CardHistoryDtoMock1;
    private CardHistoryDto CardHistoryDtoMock2;

    @Before
    public void setUp() throws Exception {
    	CardDao = new CardDao();

        // Mock 객체 생성
        CardHistoryDtoMock1 = mock(CardHistoryDto.class);
        CardHistoryDtoMock2 = mock(CardHistoryDto.class);

        when(CardDao.showPayCardList()).thenReturn(new ArrayList<CardHistoryDto>() {{
            add(CardHistoryDtoMock1);
            add(CardHistoryDtoMock2);
        }});
    }

    @Test
    public void testShowPayCardList() throws SQLException {
    	
        ArrayList<CardHistoryDto> result = CardDao.showPayCardList();

        assertEquals(2, result.size());
        
        assertEquals(CardHistoryDtoMock1, result.get(0));
        assertEquals(CardHistoryDtoMock2, result.get(1));
    }
}
