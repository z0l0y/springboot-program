package com.hust.springbootmybatisquickstart.mapper;

import com.hust.springbootmybatisquickstart.pojo.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper//ioc容器，搞bean的
public interface UserdataMapper {

    @Update("insert into userdata(name,id,phone,address)"
            + "values(#{name},#{id},#{phone},#{address})")//在表中index为自增项，所以可以不用写
    @Transactional
    void add(Noindexuserdata noindexuserdata);

    List<Userdata> list(Integer index, String name, String id, String phone, String address);

    @Select("select distinct(sum(cargo_weight))from container,logistics where container.container_id=logistics.container_id and action=#{action} and" +
            " port_name=#{portName} and CAST(action_date AS CHAR(10)) BETWEEN #{startDate} AND #{endDate}")
    Integer getTotalCargoWeight(String action, String portName, LocalDate startDate, LocalDate endDate);

    @Select("SELECT DISTINCT action_date, port_name,logistics.cargo_name, logistics.cargo_weight FROM container" +
            " INNER JOIN logistics ON container.lading_id=logistics.lading_id" +
            " WHERE container.port_name=#{portName}" +
            "  AND CAST(container.action_date AS CHAR(10)) BETWEEN #{startDate} AND #{endDate}" +
            "  AND cargo_name=#{target}" +
            "  AND container.action=#{action} order by action_date")
    List<Container> list2(String portName, LocalDate startDate, LocalDate endDate, String target, String action);

    @Select("SELECT sum(DISTINCT cargo_weight)" +
            "FROM logistics INNER JOIN container ON container.lading_id = logistics.lading_id" +
            "  WHERE action=#{action}" +
            "  AND port_name=#{portName} " +
            "  AND CAST(action_date AS CHAR(10)) BETWEEN #{startDate} AND #{endDate}" +
            "  AND cargo_name=#{cargoName}")
    Integer CargoWeightProportion(String action, String portName, LocalDate startDate, LocalDate endDate, String cargoName);

    @Select("SELECT distinct depart_port,unload.port_name,cargo_name,cargo_weight FROM unload" +
            "         INNER JOIN container ON unload.lading_id = container.lading_id" +
            "         INNER JOIN logistics ON container.lading_id = logistics.lading_id" +
            " WHERE cargo_name=#{target}" +
            "  AND CAST(action_date AS CHAR(10)) BETWEEN #{startDate} AND #{endDate}" +
            "  AND action=#{action};")
    List<Direction> list3(String target, LocalDate startDate, LocalDate endDate, String action);

    /*对客户的*/
    @Select("SELECT distinct logistics.`index`,userdata.name,lading_id,userdata.phone as userPhone,cargo_name,cargo_weight,container_id,logistics_com,linkman,company.phone as linkmanPhone" +
            "    FROM userdata" +
            "    INNER JOIN logistics ON userdata.name = logistics.owner_name" +
            "    INNER JOIN company ON logistics_com = company.name" +
            "    where userdata.name=#{name} order by cargo_name,cargo_weight")
    List<Company> list4(String name);

    /*对船的*/
    @Select("SELECT DISTINCT ship_company,ship_name,cargo_name,logistics.owner_name,linkman,company.name,company.phone" +
            " FROM `load`" +
            "    INNER JOIN logistics ON `load`.lading_id = logistics.lading_id" +
            "    INNER JOIN company ON logistics_com = company.name" +
            "    INNER JOIN container ON logistics.lading_id = container.lading_id" +
            " WHERE ship_company=#{shipCompany} and CAST(action_date AS CHAR(10)) BETWEEN #{startDate} AND #{endDate} order by ship_name,cargo_name")
    List<Boat> list5(String shipCompany, LocalDate startDate, LocalDate endDate);

    /*关于为什么可以看到index我想，前端会渲染的，怎么可能看不到，想想excel，好那我们再从客户的角度来去思考一下，总之就是从用户的角度来思考，所以能准确定位到哪一行，也不奇怪了吧*/
/*想一想用户就是数据，我们要以客户的视角来思考，为了达到目的，我要什么数据，思考完之后，
我们要切换为开发者的视角，想一想有了（不一定，只是一个虚拟的要求）之后我该怎么处理这些数据使得用户的要求得到满足*/
    @Update("update logistics set cargo_name=#{cargoName}, cargo_weight=#{cargoWeight} where `index`=#{index}")
    void update1(String cargoName, String cargoWeight, String index);

    @Update("update `load` set port_name=#{portName}, depart_part=#{departPart}, arrive_part=#{arrivePart}, load_start_time=#{loadStartTime}, " +
            "load_end_time=#{loadEndTime}, depart_time=#{departTime}, arrive_time=#{arriveTime} where lading_id=#{ladingId}")
    void update2(String portName, String departPart, String arrivePart, LocalDateTime loadStartTime,
                 LocalDateTime loadEndTime, LocalDateTime departTime, LocalDateTime arriveTime, String ladingId);

    @Update("update unload set port_name=#{portName}, depart_port=#{departPart}, arrive_port=#{arrivePart}, unload_start_time=#{unloadStartTime}, " +
            "unload_end_time=#{unloadEndTime}, depart_start_time=#{departStartTime}, arrive_end_time=#{arriveEndTime} where lading_id=#{ladingId}")
    void update3(String portName, String departPart, String arrivePart, LocalDateTime unloadStartTime,
                 LocalDateTime unloadEndTime, LocalDateTime departStartTime, LocalDateTime arriveEndTime, String ladingId);

    @Update("update container set action_date=#{actionDate}, port_name=#{port} where container_id=#{containerId} and action=#{action}")
    void update4(LocalDate actionDate, String port, String containerId, String action);

    @Delete("delete from logistics where`index`=#{index}")
    void delete1(String index);

    @Delete("delete from `load` where lading_id=#{ladingId}")
    void delete2(String ladingId);

    @Delete("delete from unload where lading_id=#{ladingId}")
    void delete3(String ladingId);

    @Delete("delete from container where lading_id=#{ladingId}")
    void delete4(String ladingId);

    @Update("insert into `load`(ship_company,ship_name, load_start_time, load_end_time, depart_time,arrive_time ," +
            "port_name ,lading_id ,container_id, container_size, depart_part, arrive_part)" +
            "values(#{shipCompany},#{shipName},#{loadStartTime} ,#{loadEndTime} ,#{departTime} ,#{arriveTime} ," +
            " #{portName} ,#{ladingId}  ,#{containerId},#{containerSize} , #{departPart}, #{arrivePart})")
    void updateLoad(String shipCompany ,String shipName,LocalDateTime loadStartTime,LocalDateTime loadEndTime,
                     LocalDateTime departTime,LocalDateTime arriveTime,
             String portName,String ladingId,String containerId,String containerSize,String departPart,String arrivePart);

    @Update("insert into unload(ship_company,ship_name, unload_start_time, unload_end_time, depart_start_time,arrive_end_time ," +
            "port_name ,lading_id ,container_id, container_size, depart_port, arrive_port)" +
            "values(#{shipCompany},#{shipName},#{unloadStartTime} ,#{unloadEndTime} ,#{departStartTime} ,#{arriveEndTime} ," +
            "#{portName} ,#{ladingId}  ,#{containerId},#{containerSize} , #{departPort}, #{arrivePort})")
    void updateUnload(String shipCompany,String shipName,LocalDateTime unloadStartTime,LocalDateTime unloadEndTime ,
                      LocalDateTime departStartTime ,LocalDateTime arriveEndTime,
         String portName ,String ladingId,String containerId,String containerSize,String departPort,String arrivePort);

    @Update("insert into container(port_name,container_id,container_size,lading_id,container_yard,action,action_date)" +
            "values(#{portName},#{containerId},#{containerSize},#{ladingId},#{containerYard},#{action},#{actionDate})")
    void updateContainer(String portName,String containerId,String containerSize,
                         String ladingId,String containerYard,String action,LocalDate actionDate);

    @Update("insert into logistics(lading_id,owner_name,owner_id,logistics_com,container_id,cargo_name,cargo_weight)"+
            "values(#{ladingId},#{ownerName},#{ownerId},#{logisticsCom},#{containerId},#{cargoName},#{cargoWeight})")
    void updateLogistics(String ladingId,String ownerName,String ownerId,String logisticsCom,
                        String containerId,String cargoName,String cargoWeight);

    @Update("INSERT INTO users (username, password, email)VALUES (#{username},#{password},#{email});")

    void enroll(String username,String password,String email);

    @Delete("delete from users where username=#{username} and password=#{password}")

    Integer logout(String username,String password);

    @Select("select count(*) from users where username=#{username} and password=#{password}")
    Integer verify(String username,String password);

}


