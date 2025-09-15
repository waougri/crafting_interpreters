#ifndef CLOX_DLL_H
#define CLOX_DLL_H

#include <stddef.h>
typedef struct DLNode {
  struct DLNode *prev;
  struct DLNode *next;
  const char *data;
} DLNode;

typedef struct DLL {
  struct DLNode *first;
  struct DLNode *last;
  size_t size;
} DLL;

#endif
