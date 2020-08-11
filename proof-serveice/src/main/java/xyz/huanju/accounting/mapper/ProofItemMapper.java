package xyz.huanju.accounting.mapper;

import xyz.huanju.accounting.domain.ProofItem;

import java.util.List;

/**
 * @author HuanJu
 * @date 2020/8/11 0:01
 */
public interface ProofItemMapper extends BaseMapper<ProofItem> {

    List<ProofItem> listBySubject(Integer subjectId);


}
