package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import exception.DBException;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.TravelEntity;
import main.java.travelbook.controller.AllQuery;
public class ExploreDao implements VisualDAO {

	@Override
	public List<Entity> getData(Entity object) throws DBException, SQLException {
		// TODO Auto-generated method stub
		ResultSet rs;
		ResultSet rs2;
		List<Entity> travels=new ArrayList<>();
		UserEntity user=(UserEntity)object;
		Connection conn=AllQuery.getInstance().getConnection();
		rs=AllQuery.getInstance().getTravels(conn, user);
		List<Integer> travelId=new ArrayList<>();
		while(rs.next()) {
			travelId.add(rs.getInt(1));
		}
		
		int count=0;
		if(travelId.size()<15) {
			user.setFollower(0);
			user.setFollowing(0);
			rs2=AllQuery.getInstance().getTravels(conn, user);
			while(rs.next()) {
				Integer val=rs2.getInt(1);
				if(!travelId.contains(val)) {
					travelId.add(val);
				}
			}
		}
		for(Integer id: travelId) {
			if(count==15)
				break;
			VisualDAO dao=DaoFactory.getInstance().createVisual(DaoType.S_TRAVEL);
			TravelEntity tr=new TravelEntity();
			tr.setIdTravel(id);
			List<Entity> results=dao.getData(tr);
			tr=(TravelEntity)results.get(0);
			travels.add(tr);
			count++;
		}
		return travels;
	}

}
