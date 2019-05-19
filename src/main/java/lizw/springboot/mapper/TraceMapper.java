package lizw.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import lizw.springboot.entity.TraceEntity;

@Mapper
public interface TraceMapper {
	
	public List<TraceEntity> selectOneIpTraceMapper(TraceEntity traceMapperEntity);

}
