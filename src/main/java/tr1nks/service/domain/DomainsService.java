package tr1nks.service.domain;

import tr1nks.domain.entity.DomainsEntity;

public interface DomainsService {
    String getEmailDomain();

    String getImagineDomain();

    String getOfficeDomain();

    DomainsEntity save(DomainsEntity domainsEntity);
}
