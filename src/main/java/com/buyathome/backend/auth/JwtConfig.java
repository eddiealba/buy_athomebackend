package com.buyathome.backend.auth;

public class JwtConfig {
    public static final String LLAVE_SECRETA = "alguna.clave.secreta.12345678";

    public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEowIBAAKCAQEA8fMyE29WTM+nXr/CzoPp+j2In1drOg0GHv/xeSU8MMyxn0wE\n" +
            "n3EbFKyMa0RaddzqsYt0DGOnh26pbACf4lI+TRgUPwLo7PITdJIAeDFj9Hrjig4j\n" +
            "ygNqNczH6rt1IEYA8dNh4FN1gnK307E/YZdDuzVyWp0A094z/qAGdmyk3u9Wgbco\n" +
            "/KGBanqB+5HbZvCyqNtPFvgSAe9wsiZ8bu8zMweDiVmiB9exVa58QNeNeTANHJZk\n" +
            "dVUaDVv/2+Q67AmLzp9ymvgB+/YgfWlAxQsKoP/NywJkGe6NYmrWt2H2lmAHPd62\n" +
            "UqrDfNbXgJZOVNJ4wVLe8UKsMXzukX1uuzrSiQIDAQABAoIBACKLU3H86nhrCX14\n" +
            "qYqswOJOmMwPQir2oxIX/tUKIO7iutWAmARftLsd66qBBt1fHk8dFwOR0uB+R0g5\n" +
            "SpqYnXaKYpGgi7sQ0QBKvDBs9NV6GjSi0i9Mj4SA6WY1P4LNG+NrB8VshRGL6X0b\n" +
            "CG6Zlz0KY3pWgkC9r/TBykpsNpnxkPntJKnciqDPVzyO9FEJeL7V6pU3DEa+64Fx\n" +
            "a7C4s9PMdXj9t0o2Sn5F93HJ+9uRmdOoejUNRS3ZNlCu2EKuzK2UrMoOk/AvsKy9\n" +
            "MW6FQQ0SfV+R2xu4KDpLFx463DX09pKi2YzSR93FffZkDXFqmiexCFs9RuBs2owx\n" +
            "vWizHXUCgYEA+fyH2aUWeFimuM5gPD8H/lKAWf1vwlFg8XZqgwzAYr0uzx7QgzUC\n" +
            "lvXU7Az6HLTsyyfmdmD7cp6gwJsPn3+2A0E6W0e6Dws1XuoELCYMZJ4W4kk2GsrR\n" +
            "KYFPn6pLCDyki1w7DiMdQtmbJp6kcoXfWjQhDBW1XMrlqveQcWry2BsCgYEA98Us\n" +
            "uNbB/JWQCw9r+3dPqb2sQeu3Rgma/keOMtV9uwY9khhs7shkhyqfps7X1247nRUL\n" +
            "ekDnwFqAlaEMZOHAFbcipyHnP3F19bzuAjdW3cKu2f7ZwYJ19AzKTXoW1kCHn62e\n" +
            "Lhc1Kw5/r5KTSE1ZEkldtN1QIb8T8M+fvOzl8isCgYBLeOxizy6rgshC/zuatMTr\n" +
            "CWlqwdMd7YfBHqkli5IBvXAGZB07n6N6hBmWcoyIYi8jXpZ7ougHQNDzlVzwIJSJ\n" +
            "AwZlTJBlvlKMp0Y/NfWD4x6xjrzg9d6UE1BAuh8RC4357Qx/RiatahknXbn0QWqf\n" +
            "RyG7DkrFCxSvuzJGq61JhQKBgQDmoAfyvSOvwl9jjCVM2k+yhbRzX78mWAaHLX+0\n" +
            "3Xe8wCBLfqTW1nyMPCaDFAFgq2Cd5FrcntGZACS0IkXxGRi8ncyJmUswiKHt7L/2\n" +
            "HCClJnOuwkNald7JHaao2z2qjEIxZV/ZMwzwWPyNJnglUqnYFaY9lcrivoMHIZtk\n" +
            "NXYMJQKBgBcfgwbo8nTWaNZLh5ITZpZdMGC2N1vbViTm0bJn5GcelF52CWUjABCY\n" +
            "pU7FZUlxZA5dU9uVza85xJtn5BpMrrVbsvQGyDKhBMR/D4GTGfwlz4W5mZ5ykG8/\n" +
            "WN+Zuli2LFF5Eon4cRMPs0xqyzAxdhKC9EAbGNq6YNInAhEDKtVB\n" +
            "-----END RSA PRIVATE KEY-----\n";

    public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8fMyE29WTM+nXr/CzoPp\n" +
            "+j2In1drOg0GHv/xeSU8MMyxn0wEn3EbFKyMa0RaddzqsYt0DGOnh26pbACf4lI+\n" +
            "TRgUPwLo7PITdJIAeDFj9Hrjig4jygNqNczH6rt1IEYA8dNh4FN1gnK307E/YZdD\n" +
            "uzVyWp0A094z/qAGdmyk3u9Wgbco/KGBanqB+5HbZvCyqNtPFvgSAe9wsiZ8bu8z\n" +
            "MweDiVmiB9exVa58QNeNeTANHJZkdVUaDVv/2+Q67AmLzp9ymvgB+/YgfWlAxQsK\n" +
            "oP/NywJkGe6NYmrWt2H2lmAHPd62UqrDfNbXgJZOVNJ4wVLe8UKsMXzukX1uuzrS\n" +
            "iQIDAQAB\n" +
            "-----END PUBLIC KEY-----";

}
