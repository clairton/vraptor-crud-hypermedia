# vraptor-crud-hypermedia[![Build Status](https://drone.io/github.com/clairton/vraptor-crud-hypermedia/status.png)](https://drone.io/github.com/clairton/vraptor-crud-hypermedia/latest)
Plugin Vraptor para juntar o projetos vraptor-crud e gson-hypermedia.

Use o tipo Results.json() na hora de serializer:

```java
private @Inject Result result;
....
result
	.use(Resuls.json())
	.from(new Pessoa())
	.serialize();
```
O exemplo acima irá retornar algo parecido com:
```javascript
{  
   "pessoa":{  
      "id":1,
      "nome":"Maria",
      "links":[  
         {  
            "href":"/pessoas/1",
            "rel":"update",
            "title":"Salvar",
            "method":"PUT",
            "type":"application/json"
         }
      ]
   }
}
```

Para coleções:

```java
result
	.use(Resuls.json())
	.from(Arrays.asList(new Pessoa()), "pessoas")
	.serialize();
```
O exemplo acima irá retornar algo parecido com:
```javascript
{  
   "pessoas":[{  
      "id":1,
      "nome":"Maria",
      "links":[  
         {  
            "href":"/pessoas/1",
            "rel":"update",
            "title":"Salvar",
            "method":"PUT",
            "type":"application/json"
         }
      ]
   }],
  "links":[  
     {  
        "href":"/pessoas/new",
        "rel":"new",
        "title":"Criar",
        "method":"GET",
        "type":"application/json"
     }
  ]
}
```


Se usar o maven, será necessário adicionar os repositórios:
```xml
<repository>
	<id>mvn-repo-releases</id>
	<url>https://raw.github.com/clairton/mvn-repo/releases</url>
</repository>
<repository>
	<id>mvn-repo-snapshot</id>
	<url>https://raw.github.com/clairton/mvn-repo/snapshots</url>
</repository>
```
 Também adicionar as depêndencias:
```xml
<dependency>
    <groupId>br.eti.clairton</groupId>
    <artifactId>vraptor-crud-hypermedia</artifactId>
    <version>0.1.0-SNAPSHOT</version><!--Ou versão mais recente-->
</dependency>
```
