package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		ResultSet rs;
		List<Integer> travelId=new ArrayList<>();
		List<Entity> travels=new ArrayList<>();
		UserEntity user=(UserEntity)object;
		Connection conn=AllQuery.getInstance().getConnection();
		String query=AllQuery.getInstance().getTravels(user);
		PreparedStatement stmt=conn.prepareStatement(query);
		try {
		
		if(stmt.getParameterMetaData().getParameterCount()==1)
			stmt.setInt(1, user.getId());
		else {
			stmt.setInt(1, user.getId());
			stmt.setInt(2, user.getId());
			stmt.setInt(3,user.getId());
		}
		rs=stmt.executeQuery();
		
		while(rs.next()) {
			travelId.add(rs.getInt(1));
		}
		}finally {
				stmt.close();
		}
		int count=0;
		if(travelId.size()<15) {
			//user.setFollower(0);
			//user.setFollowing(0);
			user=new UserEntity(user.getId());
			query=AllQuery.getInstance().getTravels(user);
			try(PreparedStatement stmt1=conn.prepareStatement(query)){
				stmt1.setInt(1, user.getId());
				rs=stmt1.executeQuery();
				while(rs.next()) {
					Integer val=rs.getInt(1);
					if(!travelId.contains(val)) {
						travelId.add(val);
					}
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
