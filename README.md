# Configuración variables de entorno

Arrancar servidor:
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"

## 1. Cargar las claves en el .env

Se cargan las claves secretas en el .env.


```
STRIPE_SECRET_KEY_TEST=sk_test_XXXXXXXXXXXXXXXXXXXXXXXX
STRIPE_SECRET_KEY_LIVE=sk_live_YYYYYYYYYYYYYYYYYYYYYYYY
STRIPE_MODE=test

```



## 2. CRear un application.properties

En este documento se asigna el valor a las claves secretas desde el .env. Esto es secreto.


```
stripe.secret-key.test=${STRIPE_SECRET_KEY_TEST}
stripe.secret-key.live=${STRIPE_SECRET_KEY_LIVE}
stripe.mode=${STRIPE_MODE}

```


## 3. Añadir dependencias al pom.xml

Se añade la dependencia para poder leer correctamente el .env

```
        <dependency>
        <groupId>io.github.cdimascio</groupId>
        <artifactId>dotenv-java</artifactId>
        <version>3.0.0</version>
        </dependency>
```

## 4. Configurar la clase principal para que funcione

En la clase main cargamos las variables de entorno manualmente.

```
static {
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
}
```

## 5. Inyectamos las propiedades con @value

Ya podemos usarlas directamente las variables e inicializarlas... 

```
@Value("${stripe.secret-key.test}")
private String testSecretKey;

@Value("${stripe.secret-key.live}")
private String liveSecretKey;

@Value("${stripe.mode}")
private String stripeMode;

@PostConstruct
public void init() {
    if ("live".equalsIgnoreCase(stripeMode)) {
        activeSecretKey = liveSecretKey;
    } else {
        activeSecretKey = testSecretKey;
    }

    Stripe.apiKey = activeSecretKey;
}

```
