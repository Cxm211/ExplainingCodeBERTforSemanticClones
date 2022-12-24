from model import Model
from transformers import (RobertaConfig, RobertaModel, RobertaTokenizer)
import torch
import os

def myfunc():

    device = torch.device("cpu")
    path = 'G:/pythontestfolder'
    clone_files = os.listdir(path)

    def read_file_java(file_name):
        with open(f'{path}/{file_name}') as f:
            lines = f.readlines()
        lines = [line.strip() for line in lines]

        method_1, method_2 = [], []
        i = lines.index('')
        m_1 = '\n '.join(lines[:i])
        m_2 = '\n '.join(lines[i:])[2:-5]

        # print("m1", m_1)
        # print("m2", m_2)

        return m_1, m_2

    clone_files

    def remove_class_info(method):

        method = method.split('\n')
        # method is a list with a line of code on each index
        clean_method = []
        first = True
        code_lines = False
        for line in method:
            if first and ('public' in line or 'private' in line or 'static' \
                          in line or 'Node' in line or 'protected' in line or 'int' in line \
                          or 'boolean' in line):
                first = False
            elif not first and ('public' in line or 'private' in line or 'static'\
                                in line or 'Node' in line or 'protected' in line or 'int' in line \
                                or 'boolean' in line):
                code_lines = True
                clean_method.append(line)
            elif not first and code_lines:
                clean_method.append(line)

        clean_method = '\n'.join(clean_method)
        return clean_method.strip()

    pairs = []
    for file_name in clone_files:
        code_1, code_2 = read_file_java(file_name)
        pairs.append((remove_class_info(code_1), code_2))

    i= 0
    for pair in pairs:
        if not pair[0] or not pair[1]:
            print(i)
        i+=1
    len(clone_files)

    BLOCK_SIZE = 400

    config = RobertaConfig.from_pretrained('roberta-base')
    model = RobertaModel.from_pretrained('microsoft/codebert-base')
    tokenizer = RobertaTokenizer.from_pretrained('roberta-base')

    model = Model(model,config,tokenizer)

    model_path = 'model/mymodel.bin'
    model.load_state_dict(torch.load(model_path, map_location=lambda storage, loc: storage), strict=False)
    model.to(device)


    def convert_examples_to_features(code_1, code_2):

        code1_tokens=tokenizer.tokenize(code_1)
        code1_tokens=code1_tokens[:BLOCK_SIZE-2]
        code1_tokens =[tokenizer.cls_token]+code1_tokens+[tokenizer.sep_token]
        code1_ids=tokenizer.convert_tokens_to_ids(code1_tokens)
        padding_length = BLOCK_SIZE - len(code1_ids)
        code1_ids+=[tokenizer.pad_token_id]*padding_length

        code2_tokens=tokenizer.tokenize(code_2)
        code2_tokens=code2_tokens[:BLOCK_SIZE-2]
        code2_tokens =[tokenizer.cls_token]+code2_tokens+[tokenizer.sep_token]
        code2_ids=tokenizer.convert_tokens_to_ids(code2_tokens)
        padding_length = BLOCK_SIZE - len(code2_ids)
        code2_ids+=[tokenizer.pad_token_id]*padding_length

        source_tokens=code1_tokens+code2_tokens
        source_ids=code1_ids+code2_ids
        return torch.tensor(source_ids).to(device)

    remove_comments = False
    predictions = []

    code_1, code_2 = pairs[0]

    iput_ids = convert_examples_to_features(code_1, code_2)
    prediction = model(iput_ids)
    print(prediction)
    prediction = int(torch.argmax(prediction[0]))
    predictions.append(prediction)
    print("prediction = "+ str(prediction))

    return prediction

print("calling :")
myfunc()