package com.thinkgem.jeesite.common.elasticsearch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Component
public class ElasticsearchProperties {
    private String schema = "http";
    @Value("${es.elasticsearch.clusterName}")
    private String clusterName = "elasticsearch";
    @NotNull(
            message = "集群节点不允许为空"
    )
    /*@Value("#{'${es.elasticsearch.clusterNodes}'.split(',')}")*/
    private List<String> clusterNodes = new ArrayList();
    private Integer connectTimeout = 1000;
    private Integer socketTimeout = 30000;
    private Integer connectionRequestTimeout = 500;
    private Integer maxConnectPerRoute = 10;
    private Integer maxConnectTotal = 30;
    private ElasticsearchProperties.Account account = new ElasticsearchProperties.Account();

    public static ElasticsearchProperties.ElasticsearchPropertiesBuilder builder() {
        return new ElasticsearchProperties.ElasticsearchPropertiesBuilder();
    }

    public String getSchema() {
        return this.schema;
    }

    public String getClusterName() {
        return this.clusterName;
    }

    public List<String> getClusterNodes() {
        return this.clusterNodes;
    }

    public Integer getConnectTimeout() {
        return this.connectTimeout;
    }

    public Integer getSocketTimeout() {
        return this.socketTimeout;
    }

    public Integer getConnectionRequestTimeout() {
        return this.connectionRequestTimeout;
    }

    public Integer getMaxConnectPerRoute() {
        return this.maxConnectPerRoute;
    }

    public Integer getMaxConnectTotal() {
        return this.maxConnectTotal;
    }

    public ElasticsearchProperties.Account getAccount() {
        return this.account;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setClusterNodes(List<String> clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public void setMaxConnectPerRoute(Integer maxConnectPerRoute) {
        this.maxConnectPerRoute = maxConnectPerRoute;
    }

    public void setMaxConnectTotal(Integer maxConnectTotal) {
        this.maxConnectTotal = maxConnectTotal;
    }

    public void setAccount(ElasticsearchProperties.Account account) {
        this.account = account;
    }

//    public boolean equals(Object o) {
//        if (o == this) {
//            return true;
//        } else if (!(o instanceof ElasticsearchProperties)) {
//            return false;
//        } else {
//            ElasticsearchProperties other = (ElasticsearchProperties)o;
//            if (!other.canEqual(this)) {
//                return false;
//            } else {
//                label119: {
//                    Object this$schema = this.getSchema();
//                    Object other$schema = other.getSchema();
//                    if (this$schema == null) {
//                        if (other$schema == null) {
//                            break label119;
//                        }
//                    } else if (this$schema.equals(other$schema)) {
//                        break label119;
//                    }
//
//                    return false;
//                }
//
//                Object this$clusterName = this.getClusterName();
//                Object other$clusterName = other.getClusterName();
//                if (this$clusterName == null) {
//                    if (other$clusterName != null) {
//                        return false;
//                    }
//                } else if (!this$clusterName.equals(other$clusterName)) {
//                    return false;
//                }
//
//                label105: {
//                    Object this$clusterNodes = this.getClusterNodes();
//                    Object other$clusterNodes = other.getClusterNodes();
//                    if (this$clusterNodes == null) {
//                        if (other$clusterNodes == null) {
//                            break label105;
//                        }
//                    } else if (this$clusterNodes.equals(other$clusterNodes)) {
//                        break label105;
//                    }
//
//                    return false;
//                }
//
//                Object this$connectTimeout = this.getConnectTimeout();
//                Object other$connectTimeout = other.getConnectTimeout();
//                if (this$connectTimeout == null) {
//                    if (other$connectTimeout != null) {
//                        return false;
//                    }
//                } else if (!this$connectTimeout.equals(other$connectTimeout)) {
//                    return false;
//                }
//
//                label91: {
//                    Object this$socketTimeout = this.getSocketTimeout();
//                    Object other$socketTimeout = other.getSocketTimeout();
//                    if (this$socketTimeout == null) {
//                        if (other$socketTimeout == null) {
//                            break label91;
//                        }
//                    } else if (this$socketTimeout.equals(other$socketTimeout)) {
//                        break label91;
//                    }
//
//                    return false;
//                }
//
//                Object this$connectionRequestTimeout = this.getConnectionRequestTimeout();
//                Object other$connectionRequestTimeout = other.getConnectionRequestTimeout();
//                if (this$connectionRequestTimeout == null) {
//                    if (other$connectionRequestTimeout != null) {
//                        return false;
//                    }
//                } else if (!this$connectionRequestTimeout.equals(other$connectionRequestTimeout)) {
//                    return false;
//                }
//
//                label77: {
//                    Object this$maxConnectPerRoute = this.getMaxConnectPerRoute();
//                    Object other$maxConnectPerRoute = other.getMaxConnectPerRoute();
//                    if (this$maxConnectPerRoute == null) {
//                        if (other$maxConnectPerRoute == null) {
//                            break label77;
//                        }
//                    } else if (this$maxConnectPerRoute.equals(other$maxConnectPerRoute)) {
//                        break label77;
//                    }
//
//                    return false;
//                }
//
//                label70: {
//                    Object this$maxConnectTotal = this.getMaxConnectTotal();
//                    Object other$maxConnectTotal = other.getMaxConnectTotal();
//                    if (this$maxConnectTotal == null) {
//                        if (other$maxConnectTotal == null) {
//                            break label70;
//                        }
//                    } else if (this$maxConnectTotal.equals(other$maxConnectTotal)) {
//                        break label70;
//                    }
//
//                    return false;
//                }
//
//                Object this$account = this.getAccount();
//                Object other$account = other.getAccount();
//                if (this$account == null) {
//                    if (other$account != null) {
//                        return false;
//                    }
//                } else if (!this$account.equals(other$account)) {
//                    return false;
//                }
//
//                return true;
//            }
//        }
//    }

    protected boolean canEqual(Object other) {
        return other instanceof ElasticsearchProperties;
    }

//    public int hashCode() {
////        int PRIME = true;
//        int result = 1;
//        Object $schema = this.getSchema();
//        result = result * 59 + ($schema == null ? 43 : $schema.hashCode());
//        Object $clusterName = this.getClusterName();
//        result = result * 59 + ($clusterName == null ? 43 : $clusterName.hashCode());
//        Object $clusterNodes = this.getClusterNodes();
//        result = result * 59 + ($clusterNodes == null ? 43 : $clusterNodes.hashCode());
//        Object $connectTimeout = this.getConnectTimeout();
//        result = result * 59 + ($connectTimeout == null ? 43 : $connectTimeout.hashCode());
//        Object $socketTimeout = this.getSocketTimeout();
//        result = result * 59 + ($socketTimeout == null ? 43 : $socketTimeout.hashCode());
//        Object $connectionRequestTimeout = this.getConnectionRequestTimeout();
//        result = result * 59 + ($connectionRequestTimeout == null ? 43 : $connectionRequestTimeout.hashCode());
//        Object $maxConnectPerRoute = this.getMaxConnectPerRoute();
//        result = result * 59 + ($maxConnectPerRoute == null ? 43 : $maxConnectPerRoute.hashCode());
//        Object $maxConnectTotal = this.getMaxConnectTotal();
//        result = result * 59 + ($maxConnectTotal == null ? 43 : $maxConnectTotal.hashCode());
//        Object $account = this.getAccount();
//        result = result * 59 + ($account == null ? 43 : $account.hashCode());
//        return result;
//    }

    public String toString() {
        return "ElasticsearchProperties(schema=" + this.getSchema() + ", clusterName=" + this.getClusterName() + ", clusterNodes=" + this.getClusterNodes() + ", connectTimeout=" + this.getConnectTimeout() + ", socketTimeout=" + this.getSocketTimeout() + ", connectionRequestTimeout=" + this.getConnectionRequestTimeout() + ", maxConnectPerRoute=" + this.getMaxConnectPerRoute() + ", maxConnectTotal=" + this.getMaxConnectTotal() + ", account=" + this.getAccount() + ")";
    }

    public ElasticsearchProperties() {
    }

    public ElasticsearchProperties(String schema, String clusterName, List<String> clusterNodes, Integer connectTimeout, Integer socketTimeout, Integer connectionRequestTimeout, Integer maxConnectPerRoute, Integer maxConnectTotal, ElasticsearchProperties.Account account) {
        this.schema = schema;
        this.clusterName = clusterName;
        this.clusterNodes = clusterNodes;
        this.connectTimeout = connectTimeout;
        this.socketTimeout = socketTimeout;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.maxConnectPerRoute = maxConnectPerRoute;
        this.maxConnectTotal = maxConnectTotal;
        this.account = account;
    }

    public static class ElasticsearchPropertiesBuilder {
        private String schema;
        private String clusterName;
        private List<String> clusterNodes;
        private Integer connectTimeout;
        private Integer socketTimeout;
        private Integer connectionRequestTimeout;
        private Integer maxConnectPerRoute;
        private Integer maxConnectTotal;
        private ElasticsearchProperties.Account account;

        ElasticsearchPropertiesBuilder() {
        }

        public ElasticsearchProperties.ElasticsearchPropertiesBuilder schema(String schema) {
            this.schema = schema;
            return this;
        }

        public ElasticsearchProperties.ElasticsearchPropertiesBuilder clusterName(String clusterName) {
            this.clusterName = clusterName;
            return this;
        }

        public ElasticsearchProperties.ElasticsearchPropertiesBuilder clusterNodes(List<String> clusterNodes) {
            this.clusterNodes = clusterNodes;
            return this;
        }

        public ElasticsearchProperties.ElasticsearchPropertiesBuilder connectTimeout(Integer connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public ElasticsearchProperties.ElasticsearchPropertiesBuilder socketTimeout(Integer socketTimeout) {
            this.socketTimeout = socketTimeout;
            return this;
        }

        public ElasticsearchProperties.ElasticsearchPropertiesBuilder connectionRequestTimeout(Integer connectionRequestTimeout) {
            this.connectionRequestTimeout = connectionRequestTimeout;
            return this;
        }

        public ElasticsearchProperties.ElasticsearchPropertiesBuilder maxConnectPerRoute(Integer maxConnectPerRoute) {
            this.maxConnectPerRoute = maxConnectPerRoute;
            return this;
        }

        public ElasticsearchProperties.ElasticsearchPropertiesBuilder maxConnectTotal(Integer maxConnectTotal) {
            this.maxConnectTotal = maxConnectTotal;
            return this;
        }

        public ElasticsearchProperties.ElasticsearchPropertiesBuilder account(ElasticsearchProperties.Account account) {
            this.account = account;
            return this;
        }

        public ElasticsearchProperties build() {
            return new ElasticsearchProperties(this.schema, this.clusterName, this.clusterNodes, this.connectTimeout, this.socketTimeout, this.connectionRequestTimeout, this.maxConnectPerRoute, this.maxConnectTotal, this.account);
        }

        public String toString() {
            return "ElasticsearchProperties.ElasticsearchPropertiesBuilder(schema=" + this.schema + ", clusterName=" + this.clusterName + ", clusterNodes=" + this.clusterNodes + ", connectTimeout=" + this.connectTimeout + ", socketTimeout=" + this.socketTimeout + ", connectionRequestTimeout=" + this.connectionRequestTimeout + ", maxConnectPerRoute=" + this.maxConnectPerRoute + ", maxConnectTotal=" + this.maxConnectTotal + ", account=" + this.account + ")";
        }
    }

    public class Account {
        private String username;
        private String password;

        public Account() {
        }

        public String getUsername() {
            return this.username;
        }

        public String getPassword() {
            return this.password;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof ElasticsearchProperties.Account)) {
                return false;
            } else {
                ElasticsearchProperties.Account other = (ElasticsearchProperties.Account)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$username = this.getUsername();
                    Object other$username = other.getUsername();
                    if (this$username == null) {
                        if (other$username != null) {
                            return false;
                        }
                    } else if (!this$username.equals(other$username)) {
                        return false;
                    }

                    Object this$password = this.getPassword();
                    Object other$password = other.getPassword();
                    if (this$password == null) {
                        if (other$password != null) {
                            return false;
                        }
                    } else if (!this$password.equals(other$password)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof ElasticsearchProperties.Account;
        }

//        public int hashCode() {
////            int PRIME = true;
//            int result = 1;
//            Object $username = this.getUsername();
//            result = result * 59 + ($username == null ? 43 : $username.hashCode());
//            Object $password = this.getPassword();
//            result = result * 59 + ($password == null ? 43 : $password.hashCode());
//            return result;
//        }

        public String toString() {
            return "ElasticsearchProperties.Account(username=" + this.getUsername() + ", password=" + this.getPassword() + ")";
        }
    }
}

