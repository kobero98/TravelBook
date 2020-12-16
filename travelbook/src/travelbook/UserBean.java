package travelbook;

public class UserBean{
	private String Name,Surname,Description,Gender,URLphoto;
	private int  id,NFollower,NFollowing,NTrip;
	
	public void setId(int n){
		this.id=n;
	}
	public void setName(String name)
	{
		this.Name=name;
	}
	public void setSurname(String surname)
	{
		this.Surname=surname;
	}
	public void setDescription(String Description)
	{
		this.Description=Description;
	}
	public void setUrlPhoto(String url)
	{
		this.URLphoto=url;
	}
	public void setSex(String Gender)
	{
		this.Gender=Gender;
	}
	public void setFollower(int NFollower)
	{
		this.NFollower=NFollower;
	}
	public void setFollowing(int NFollowing)
	{
		this.NFollowing=NFollowing;
	}
	public void setTrip(int Ntrip)
	{
		this.NTrip=Ntrip;
	}

	public int getId()
	{
		return this.id;
	}
	public String getName()
	{
		return this.Name;
	}
	public String getSurname()
	{
		return this.Surname;
	}
	public String getDescription()
	{
		return this.Description;
	}
	public String getUrlPhoto()
	{
		return this.URLphoto;
	}
	public String getGender()
	{
		return this.Gender;
	}
	public int getNFollower()
	{
		return this.NFollower;
	}
	public int getNFollowing()
	{
		return this.NFollowing;
	}
	public int getNTrip()
	{
		return this.NTrip;
	}

}
